package com.task.auth.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.task.auth.jwt.JwtHandler;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {
	private final JwtHandler jwtHandler;

	@Around("@annotation(auth)")
	public Object around(ProceedingJoinPoint pjp, Auth auth) throws Throwable {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attrs == null) throw new UnauthorizedException("no_request_context");
		HttpServletRequest req = attrs.getRequest();

		String authz = req.getHeader("Authorization");
		if (authz == null || !authz.startsWith("Bearer ")) throw new UnauthorizedException("missing_bearer_token");
		String token = authz.substring(7);

		var jws = jwtHandler.parse(token);
		Claims c = jws.getBody();
		var principal = new AuthContext.Principal(
			Long.valueOf(c.getSubject()),
			(String)c.get("email"),
			(String)c.get("name"),
			(String)c.get("role")
		);

		// role 체크
		String[] roles = auth.roles();
		if (roles.length > 0 && Arrays.stream(roles).noneMatch(r -> r.equalsIgnoreCase(principal.role()))) {
			throw new ForbiddenException("forbidden: role " + principal.role());
		}

		try {
			AuthContext.set(principal);
			return pjp.proceed();
		} finally {
			AuthContext.clear();
		}
	}
}
