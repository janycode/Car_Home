package com.autohome.server.service;

import com.autohome.common.dto.NewsDetailsDto;
import com.autohome.common.dto.NewsDetailsIdDto;
import com.autohome.common.vo.R;

/**
 * @Interface: NewsService
 * @Description:
 * @Author: Jerry(姜源)
 * @Create: 2020/08/08 15:12
 */

public interface NewsService {
    R addNews(NewsDetailsDto dto);

    R delNews(Integer id);

    R updateNews(NewsDetailsIdDto dto);

    R getAllNewss(Integer pageNum, Integer pageSize);

    R getNewsById(Integer id);

    R getNewsByPage(Integer pageNum, Integer pageSize, Integer newsType);
}
