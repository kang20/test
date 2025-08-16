package com.task.auth.aop;

public class AuthContext {
	public record Principal(Long id, String email, String name, String role) {}
	private static final ThreadLocal<Principal> TL = new ThreadLocal<>();
	static void set(Principal p){ TL.set(p); }
	public static Principal current(){ return TL.get(); }
	static void clear(){ TL.remove(); }
}
