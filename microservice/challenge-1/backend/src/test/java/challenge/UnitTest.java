package challenge;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import challenge.model.User;

public class UnitTest {

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void test() {
		String json = asJsonString(new User("thiago5", "thiago5"));
		assertEquals("{\"name\":\"thiago5\",\"pass\":\"thiago5\"}", json);
	}

}
