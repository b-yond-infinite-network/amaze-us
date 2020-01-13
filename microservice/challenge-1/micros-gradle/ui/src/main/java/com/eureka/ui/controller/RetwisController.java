package com.eureka.ui.controller;


import com.eureka.common.domain.Post;
import com.eureka.common.domain.Range;
import com.eureka.common.domain.User;
import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.common.web.NoSuchDataException;
import com.eureka.common.web.WebPost;
import com.eureka.ui.RetwisSecurity;
import com.eureka.ui.service.UiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Optional;

import static com.eureka.ui.web.CookieInterceptor.RETWIS_COOKIE;
import static com.eureka.ui.web.CookieInterceptor.RETWIS_COOKIE_USER;
import static com.eureka.ui.web.CookieInterceptor.RETWIS_COOKIE_USER_ID;

/**
 * Annotation-driven controller for Retwis.
 *
 * @author Costin Leau
 */
@Controller
public class RetwisController {

    private UiService retwis;

    public RetwisController(UiService retwis) {
        this.retwis = retwis;
    }

    @RequestMapping("/")
    public String root(@RequestParam(required = false) Integer page, Model model) {
        if (RetwisSecurity.isSignedIn()) {
            return "redirect:/!" + RetwisSecurity.getName();
        }
        return timeline(page, model);
    }

    @RequestMapping("/timeline")
    public String timeline(@RequestParam(required = false) Integer page, Model model) {
        // sanitize page attribute
        page = (page != null ? Math.abs(page) : 1);
        model.addAttribute("page", page + 1);
        Range range = new Range(page);
        //model.addAttribute("moreposts", retwis.hasMoreTimeline(range));
        //model.addAttribute("posts", retwis.timeline(page));
        //model.addAttribute("users", retwis.newUsers(new Range()));
        return "timeline";
    }

    @RequestMapping("/signUp")
    public String signUp(String name, String pass, String pass2, Model model, HttpServletResponse response) {
        if (retwis.isUserValid(name).isPresent()) {
            model.addAttribute("errorduplicateuser", Boolean.TRUE);
            return "signin";
        }

        if (!StringUtils.hasText(pass) || !StringUtils.hasText(pass2) || !pass.equals(pass2)) {
            model.addAttribute("errormatch", Boolean.TRUE);
            return "signin";
        }

        Optional<User> newUser = retwis.addUser(name, pass);

        newUser.ifPresent(user -> addAuthCookie(user, response));


        return "redirect:/!" + name;
    }


    private void addAuthCookie(User user, HttpServletResponse response) {
        RetwisSecurity.setUser(user.getUsername(), String.valueOf(user.getId()), user.getAuthKey());

        Cookie cookie = new Cookie(RETWIS_COOKIE, user.getAuthKey());
        cookie.setComment("Retwis-J demo");
        // cookie valid for up to 1 week
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        Cookie cookie1 = new Cookie(RETWIS_COOKIE_USER_ID, String.valueOf(user.getId()));
        Cookie cookie2 = new Cookie(RETWIS_COOKIE_USER, String.valueOf(user.getUsername()));
        cookie2.setMaxAge(60*60);
        response.addCookie(cookie2);
        cookie1.setMaxAge(60*60);
        response.addCookie(cookie1);
        response.addHeader("Authorization", user.getAuthKey());
    }



    @RequestMapping("/signIn")
    public String signIn(@RequestParam(required = false) String name, @RequestParam(required = false) String pass, Model model, HttpServletResponse response) {
        // add tracing cookie

        System.out.println("user: "+ name);

        System.out.println("pass: "+ pass);

        if (name != null && pass != null ){
            Optional<User> user = retwis.auth(name, pass);
            if (user.isPresent()) {
                addAuthCookie(user.get(), response);
                return "redirect:/!" + name;
            }
        }

        else if (StringUtils.hasText(name) || StringUtils.hasText(pass)) {
            model.addAttribute("errorpass", Boolean.TRUE);
        }
        // go back to sign in screen
        return "signin";
    }



    @RequestMapping(value = "/!{name}", method = RequestMethod.GET)
    public String posts(@PathVariable String name, @RequestParam(required = false) String replyto,
                        @RequestParam(required = false) String replypid, @RequestParam(required = false) Integer page, Model model,
                        @CookieValue(value = RETWIS_COOKIE, defaultValue = "Atta") String token) {
        checkUser(name);


        Optional<Long> userId = retwis.findUid(name);
        String targetUid = "";
        if (userId.isPresent()){
            targetUid=userId.get().toString();
        }


        model.addAttribute("post", new Post());
        model.addAttribute("name", name);
        model.addAttribute("followers", retwis.getFollowers(targetUid, token));
        model.addAttribute("following", retwis.getFollowing(targetUid,  token));

        if (RetwisSecurity.isSignedIn()) {
            model.addAttribute("replyTo", replyto);
            model.addAttribute("replyPid", replypid);

            if (!targetUid.equals(RetwisSecurity.getUid())) {
                //model.addAttribute("also_followed", retwis.alsoFollowed(RetwisSecurity.getUid(), targetUid));
                model.addAttribute("common_followers", retwis.commonFollowers(RetwisSecurity.getUid(), targetUid));
                model.addAttribute("follows", retwis.isFollowing(RetwisSecurity.getUid(), targetUid));
            }
        }
        // sanitize page attribute
        page = (page != null ? Math.abs(page) : 1);
        model.addAttribute("page", page + 1);
        Range range = new Range(page);
        model.addAttribute("moreposts", (RetwisSecurity.isUserSignedIn(targetUid) ? retwis.hasMoreTimeline(targetUid,page)
                //: retwis.hasMorePosts(targetUid, range)));
        : false));
        model.addAttribute("posts", (RetwisSecurity.isUserSignedIn(targetUid) ? retwis.getTimeline(targetUid, page)
                //: retwis.getPosts(targetUid, range)));
                : new ArrayList<>()));

        return "home";
    }

    private Long checkUser(String username) {
        Optional<Long> user = retwis.isUserValid(username);
        if (!user.isPresent()) {
            throw new NoSuchDataException(username, true);
        }

        return user.get();
    }

    @RequestMapping("/logout")
    public String logout() {
        String user = RetwisSecurity.getName();
        // invalidate auth
        //retwis.deleteAuth(user);

        RetwisSecurity.clean();

        return "redirect:/";
    }

    @RequestMapping(value = "/!{name}", method = RequestMethod.POST)
    public String posts(@PathVariable String name, WebPost post, Model model, HttpServletRequest request, @CookieValue(value = RETWIS_COOKIE, defaultValue = "Atta") String token) {
        Long userId = checkUser(name);
        retwis.post(userId, post, token);
        return "redirect:/!" + name;
    }

    @RequestMapping("/!{name}/follow")
    public String follow(@PathVariable String name) {
        checkUser(name);
        retwis.follow(name);
        return "redirect:/!" + name;
    }

    @RequestMapping("/!{name}/stopfollowing")
    public String stopFollowing(@PathVariable String name) {
        checkUser(name);
        retwis.stopFollowing(name);
        return "redirect:/!" + name;
    }



    @RequestMapping("/!{name}/mentions")
    public String mentions(@PathVariable String name, Model model) {
        checkUser(name);
        model.addAttribute("name", name);
        Optional<Long> userId = retwis.findUid(name);

        String targetUid = "";
        if (userId.isPresent()){
            targetUid=userId.get().toString();
        }

        //model.addAttribute("posts", retwis.getMentions(targetUid, new Range()));
        model.addAttribute("followers", retwis.getFollowers(targetUid, RetwisSecurity.getToken()));
        model.addAttribute("following", retwis.getFollowing(targetUid, RetwisSecurity.getToken()));

        if (RetwisSecurity.isSignedIn() && !targetUid.equals(RetwisSecurity.getUid())) {
            model.addAttribute("also_followed", retwis.alsoFollowed(RetwisSecurity.getUid(), targetUid));
            model.addAttribute("common_followers", retwis.commonFollowers(RetwisSecurity.getUid(), targetUid));
            model.addAttribute("follows", retwis.isFollowing(RetwisSecurity.getUid(), targetUid));
        }

        return "mentions";
    }




    @ExceptionHandler(NoSuchDataException.class)
    public String handleNoUserException(NoSuchDataException ex) {
        //		model.addAttribute("data", ex.getData());
        //		model.addAttribute("nodatatype", ex.isPost() ? "nodata.post" : "nodata.user");
        return "nodata";
    }


    @RequestMapping("/status")
    public String status(String pid, Model model) {
        Optional <TweetDTO> res = checkPost(pid);
        WebPost post = retwis.getPost(res);
        model.addAttribute("posts", post);
        return "statis";
    }

    private Optional <TweetDTO> checkPost(String pid) {

        Optional <TweetDTO> res = retwis.isPostValid(pid);
        if (!res.isPresent()) {
            throw new NoSuchDataException(pid, false);
        }

        return res;
    }


}