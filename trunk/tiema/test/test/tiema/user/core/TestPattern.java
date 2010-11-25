package test.tiema.user.core;

import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.Test;


public class TestPattern extends TestCase{

	@Test
	public void testFuck(){
		String s = "result((\\[\\d+\\])(\\[1\\])?)*(\\..*)?";
		assertTrue(Pattern.matches(s,"result[0][1].address"));
		assertTrue(Pattern.matches(s,"result[0][1]"));
		assertTrue(Pattern.matches(s,"result[0]"));
		assertTrue(Pattern.matches(s,"result"));
	}
}
