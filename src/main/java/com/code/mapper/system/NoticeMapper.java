package com.code.mapper.system;


import com.code.entity.system.Notice;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

public interface NoticeMapper extends Mapper<Notice> {

    List<Notice> selectPageList(HashMap<String, Object> map);

    Notice selectById(Long noticeId);

    int updateStatus(Long noticeId);

}
