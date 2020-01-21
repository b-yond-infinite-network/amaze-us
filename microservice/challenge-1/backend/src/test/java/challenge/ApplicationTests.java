package challenge;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import challenge.model.User;
import challenge.web.WebPost;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void rootTest() throws Exception {
		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("retwisj")));
	}

	@Test
	public void fullTest() throws Exception {
		User user = new User("thiago", "thiago");

		// create user
		mockMvc.perform(post("/users").content(asJsonString(user)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// get user
		mockMvc.perform(get("/users/thiago")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(2))).andExpect(jsonPath("$.name", is("thiago")))
				.andExpect(jsonPath("$.id", is(greaterThan(0))));

		// create posts for user
		WebPost webPost = new WebPost();
		webPost.setContent("post xxx");
		mockMvc.perform(post("/users/thiago/posts").content(asJsonString(webPost))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		// get posts from user
		mockMvc.perform(get("/users/thiago/posts")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void getUserNotFoundTest() throws Exception {
		mockMvc.perform(get("/users/invaliduser1234")).andDo(print()).andExpect(status().isNotFound());
		// mockMvc.perform(get("/users/")).andDo(print()).andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void creatUserTest() throws Exception {
		// String usernameAndPassword = "thiago8";
		// User user = new User(usernameAndPassword, usernameAndPassword);
		// String json = asJsonString(user);
		// assertEquals("{\"name\":\"thiago5\",\"pass\":\"thiago5\"}", json);

		// mockMvc.perform(
		// post("/users").content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		// .andExpect(status().isCreated()).andExpect(jsonPath("$.name",
		// is(usernameAndPassword)));
	}

}