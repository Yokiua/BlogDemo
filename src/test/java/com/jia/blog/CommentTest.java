package com.jia.blog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.blog.pojo.Comment;
import com.jia.blog.service.BlogCommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
public class CommentTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BlogCommentService blogCommentService;
    @Test
    public void t1() throws JsonProcessingException {
        List<Comment> comment = blogCommentService.getComment(1L);

        System.out.println(objectMapper.writeValueAsString(comment));
    }
}
