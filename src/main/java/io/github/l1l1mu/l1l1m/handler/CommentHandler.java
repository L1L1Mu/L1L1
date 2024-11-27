package io.github.l1l1mu.l1l1m.handler;

import io.github.l1l1mu.l1l1m.model.Article;
import io.github.l1l1mu.l1l1m.model.Comment;
import io.github.l1l1mu.l1l1m.model.User;
import io.github.l1l1mu.l1l1m.repository.ArticleRepository;
import io.github.l1l1mu.l1l1m.repository.CommentRepository;
import io.github.l1l1mu.l1l1m.repository.UserRepository;
import io.github.l1l1mu.l1l1m.security.Secured;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.HashMap;
import java.util.Map;

@Path("/comments")
public class CommentHandler {

    @Inject
    private CommentRepository commentRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ArticleRepository articleRepository;

    @POST
    @Path("/")
    @Secured({"user", "admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(Comment comment, @Context SecurityContext securityContext) {
        User user = userRepository.findByID(Integer.valueOf(securityContext.getUserPrincipal().getName()));
        comment.setUser(user);
        Article article = articleRepository.findByID(comment.getArticleId());
        comment.setArticle(article);
        comment.setCreatedAt(System.currentTimeMillis() / 1000);
        commentRepository.create(comment);
        Map<String, Object> res = new HashMap<>();
        res.put("code", Response.Status.OK);
        return Response.status(Response.Status.OK).entity(res).build();
    }
}
