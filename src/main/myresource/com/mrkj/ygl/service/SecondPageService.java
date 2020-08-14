package com.mrkj.ygl.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public long getSecondCount(String mainId){
        String sql = "SELECT count(sec_id) AS count FROM my_second WHERE main_id=? ";
        return (long)jdbc.queryForMap(sql, mainId).get("count");
    }

    public int saveSecondPage(String mainId, String content, String mainCreateuser){
        String sql = "INSERT INTO my_second (sec_id,main_id,sec_content,sec_createtime,sec_createuser,sec_sequence)"+
                "VALUES (?,?,?,NOW(), ?,1)";
        return jdbc.update(sql, UUID.randomUUID().toString(), mainId, content, mainCreateuser);
    }

    public String getPage(long count, Integer currentPage, Integer offset, Map<String, String> param){

        long currentLong = (long)currentPage;
        long countPage = 0;
        //计算所需要总页数
        if(count%offset != 0){
            //不为0，则需要增加一页
            countPage = count/offset +1;
        }else{
            countPage = count/offset;
        }
        StringBuilder sbParam = new StringBuilder();

        if(param != null){
            Set<Map.Entry<String, String>> paramSet = param.entrySet();
            for(Map.Entry<String, String> entry: paramSet){
                sbParam.append("&"+entry.getKey()+"="+entry.getValue());
            }
        }

        StringBuilder sb = new StringBuilder();

        //判断是否第一页，定义“上一页”按钮的功能
        if(currentPage > 1){
            sb.append("<span class=\"page\"> <a href=\"?page=" + (currentPage-1) );
            sb.append(sbParam);
            sb.append(" \"> << >></a> <span>");
        }else{
            sb.append("<span class=\"page\"> <a href=\"?page=1" );
            sb.append(sbParam);
            sb.append(" \"> << >></a> <span>");
        }
        //第一页
        sb.append("<span class=\"page\" style=\"width:50px !important;\"> ");
        sb.append("<a href=\"?page=1");
        sb.append(param);
        sb.append("\"> start </a> </span>");
        //如果总页数减去当前页数大于5， 那么证明可以显示五个分页
        if((countPage-currentPage) > 5){
            for(long i=currentPage; i<currentPage+5; i++){
                sb.append("<span class=\"page\"> <a href=\"?page= " + i);
                sb.append(sbParam);
                sb.append("\">" +i+ "</a> </span>");
            }
        }else if(countPage - 4 >0){
            //总页数满5页
            for(long i= countPage-4; i<=countPage; i++){
                sb.append("<span class=\"page\"> <a href=\"?page= " + i);
                sb.append(sbParam);
                sb.append("\">" +i+ "</a> </span>");
            }
        }else{
            //总页数不满5页，
            for(long i=1; i<=countPage; i++){
                sb.append("<span class=\"page\"> <a href=\"?page= " + i);
                sb.append(sbParam);
                sb.append("\">" +i+ "</a> </span>");
            }
        }
        sb.append("<span class=\"page\" style=\"width: 40px !important;\">");
        sb.append("<a href=\"?page=" + (countPage == 0?1:countPage));
        sb.append(sbParam);
        sb.append("\"> end </a> </span>");

        //定义“下一页”按钮功能
        if(currentLong < currentPage){
            //存在下一页
            sb.append("<span class=\"page\"> <a href=\"?page=" + (currentLong+1) );
            sb.append(sbParam);
            sb.append(" \"> >> >></a> <span>");
        }else{
            sb.append("<span class=\"page\"> <a href=\"?page=" + (currentLong) );
            sb.append(sbParam);
            sb.append(" \"> << >></a> <span>");
        }
        sb.append("<span> 共" + countPage + "页<span>");

        return sb.toString();

    }
}
