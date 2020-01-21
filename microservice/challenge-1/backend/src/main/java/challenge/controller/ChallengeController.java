package challenge.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import challenge.model.Range;
import challenge.model.User;
import challenge.redis.RetwisRepository;
import challenge.web.NoSuchDataException;
import challenge.web.WebPost;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ChallengeController {
	private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);

	@Autowired
	private final RetwisRepository repository;

	@Autowired
	public ChallengeController(RetwisRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/")
	public String root() {
		return "retwisj";
	}

	@GetMapping("/users")
	public List<String> getUsers(@RequestParam(required = false) Integer page) {
		page = (page != null ? Math.abs(page) : 1);

		return repository.newUsers(new Range(page));
	}

	@PostMapping("/users")
	public ResponseEntity<?> addUser(@RequestBody User user) throws URISyntaxException {
		logger.info("User: {}", user);

		String id = repository.findUid(user.getName());
		if (id != null)
			return ResponseEntity.badRequest().body("User already exist");

		String auth = repository.addUser(user.getName(), user.getPass());
		logger.info(auth);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(user.getName())
				.toUri();

		return ResponseEntity.created(uri).body(user);
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) {
		String id = repository.findUid(username);
		if (id == null)
			return ResponseEntity.notFound().build();

		User user = new User(Long.parseLong(id), username);

		return ResponseEntity.ok().body(user);
	}

	@GetMapping("/login/{username}")
	public ResponseEntity<Void> login(@PathVariable String username) {
		if (!repository.auth(username, username))
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().build();
	}

	@GetMapping("/users/{username}/posts")
	public List<WebPost> getPosts(@PathVariable String username, @RequestParam(required = false) Integer page) {
		checkUser(username);

		String userId = repository.findUid(username);
		page = (page != null ? Math.abs(page) : 1);

		return repository.getPosts(userId, new Range(page));
	}

	@PostMapping("/users/{username}/posts")
	public ResponseEntity<?> createPost(@PathVariable String username, @RequestBody WebPost webPost)
			throws URISyntaxException {
		logger.info("Post: {}", webPost);

		String pid = repository.post(username, webPost);
		webPost.setPid(pid);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(pid).toUri();

		return ResponseEntity.created(uri).body(webPost);
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<WebPost> getPost(@PathVariable String id) {
		checkPost(id);

		List<WebPost> webPosts = repository.getPost(id);
		if (webPosts.size() <= 0)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().body(webPosts.get(0));
	}

	private void checkUser(String username) {
		if (!repository.isUserValid(username)) {
			throw new NoSuchDataException(username, true);
		}
	}

	private void checkPost(String pid) {
		if (!repository.isPostValid(pid)) {
			throw new NoSuchDataException(pid, false);
		}
	}
}