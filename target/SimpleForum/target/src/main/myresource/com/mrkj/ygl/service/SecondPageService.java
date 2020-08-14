package com.mrkj.ygl.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SecondPageService {

    @Resource
    JdbcTemplate jdbc;

    public Map<String, Object> getMainAndSeconds(String mainId, Integer start, Integer offset){
        String sql_select_mymain = "SELECT main_id, main_title, main_content, DATE_FORMAT(main_createtime,'%Y年%M月%D日 %h时%i分%s秒')" +
                "as main_createtime, main_createuser, main_commend FROM my_main WHERE main_id=?";
        String sql_select_mysecond = "SELECT sec_id, main_id, sec_content, DATE_FORMAT(sec_createtime,'%Y年%M月%D日 %h时%i分%s秒')" +
                "as sec_createtime, sec_createuser, sec_sequence FROM my_second WHERE main_id = ? ORDER BY sec_createtime"
                +" LIMIT ?,?" ;
        Map<String, Object> maincontent = jdbc.queryForMap(sql_select_mymain, mainId);
        if(maincontent != null){
            List<Map<String, Object>> seconds = jdbc.queryForList(sql_select_mysecond, mainId, start, offset);
            maincontent.put("seconds", seconds);
        }

        return maincontent;
    }

    public int saveSecondPage(String mainId, String content, String mainCreateuser){
        String sql = "INSERT INTO my_second (sec_id,main_id,sec_content,sec_createtime,sec_createuser,sec_sequence)"+
                "VALUES (?,?,?,NOW(), ?,1)";
        return jdbc.update(sql, UUID.randomUUID().toString(), mainId, content, mainCreateuser);
    }
}
