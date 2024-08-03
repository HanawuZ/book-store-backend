package com.backend.app.security.oauth2.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import com.backend.app.models.entities.User;
import com.backend.app.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* 
 Attributes:
login: HanawuZ
id: 110051299
node_id: U_kgDOBo8_4w
avatar_url: https://avatars.githubusercontent.com/u/110051299?v=4
gravatar_id:
url: https://api.github.com/users/HanawuZ
html_url: https://github.com/HanawuZ
followers_url: https://api.github.com/users/HanawuZ/followers
following_url: https://api.github.com/users/HanawuZ/following{/other_user}
gists_url: https://api.github.com/users/HanawuZ/gists{/gist_id}
starred_url: https://api.github.com/users/HanawuZ/starred{/owner}{/repo}
subscriptions_url: https://api.github.com/users/HanawuZ/subscriptions
organizations_url: https://api.github.com/users/HanawuZ/orgs
repos_url: https://api.github.com/users/HanawuZ/repos
events_url: https://api.github.com/users/HanawuZ/events{/privacy}
received_events_url: https://api.github.com/users/HanawuZ/received_events
type: User
site_admin: false
name: Thanawut Tuamprajak
company: Suranaree University of Technology
blog:
location: Thailand
email: null
hireable: null
bio: Greeting. I'm Thanawut Tuamprajak (called me `HanawuZ`).
I'm junior computer engineer that experiencing fullstack, machine learning and data engineer
twitter_username: null
public_repos: 25
public_gists: 0
followers: 11
following: 24
created_at: 2022-07-26T16:22:43Z
updated_at: 2024-04-29T06:37:30Z
private_gists: 0
total_private_repos: 15
owned_private_repos: 14
disk_usage: 338908
collaborators: 7
two_factor_authentication: true
plan: {name=free, space=976562499, collaborators=0, private_repos=10000}
*/
@Component
public class GitHubOAuth2SuccessHandler {

    private UserService userService;

    @Autowired
    public GitHubOAuth2SuccessHandler(UserService userService) {
        this.userService = userService;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response,
            OAuth2AuthenticationToken authenticationToken) throws IOException, ServletException {
        try {
            DefaultOAuth2User oauth2User = (DefaultOAuth2User) authenticationToken.getPrincipal();
            Map<String, Object> userAttributes = oauth2User.getAttributes();

            Map<String, Object> userMap = new HashMap<>();
            userMap.put("username", userAttributes.get("login"));   
            userMap.put("providerId", userAttributes.get("id"));
            userMap.put("provider", "GitHub");
            userMap.put("profilePicture", userAttributes.get("avatar_url"));
            userMap.put("fullname", userAttributes.get("name"));
            userMap.put("email", userAttributes.get("email"));

            User user = this.userService.upsertUser(userMap);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "OAuth 2.0 authentication failed");
                return;
            }
            response.sendRedirect("/");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "OAuth 2.0 authentication failed");
        }
    }
}
