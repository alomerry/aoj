package mo.entity.vo.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.main.News;
import mo.entity.po.main.User;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NewsUserLink {
    private User user;
    private News news;

    public NewsUserLink(User user, News news) {
        this.user = user;
        this.news = news;
    }
}
