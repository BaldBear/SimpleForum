package com.mrkj.ygl.service;
import freemarker.template.SimpleDate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MainPageService {

    @Resource
    JdbcTemplate jdbc;

    @Resource
    SimpleDateFormat sdf;

    public int saveMainContent(String content, String mainTitle, String mainCreateUser){
        //定义sql语句
        String sql = "INSERT INTO my_main (main_id, main_title, main_content, main_createtime, main_createuser, main_commend)"+
                "VALUES(?, ?, ?, ?, ?, ?)";
        String sql_save_myinfo = "INSERT INTO my_info (main_id, info_reply, info_see, info_lastuser, info_lasttime)" +
                "VALUES(?,0,0,?,?)";
        String mainID = UUID.randomUUID().toString();
        sdf.applyPattern("YYYY-MM-DD hh:mm:ss");
        String mainCreateTime = sdf.format(new Date());
        Integer mainCommend = 0;
        jdbc.update(sql_save_myinfo, mainID, mainCreateUser, mainCreateTime);
        return jdbc.update(sql, mainID, mainTitle, content, mainCreateTime, mainCreateUser, mainCommend);
    }
    public List<Map<String, Object>> getMainPage(int row, int offset){
        //left join用于查找左表全部元素和右表中符合查询条件的列，limit用于限制查询结果返回的个数
        String sql = "SELECT main.*, info.info_id, info.info_reply, info.info_see, info.info_lastuser,"+
                "info.info_lasttime FROM mrbbs.my_main as main LEFT JOIN mrbbs.my_info as info on main.main_id = "+
                "info.main_id ORDER BY main.main_commend, main.main_createtime desc limit ?, ?";

        return jdbc.queryForList(sql, row, offset);
    }

    public long getMainCount(){
        String sql = "SELECT count(main_id) as count from my_main";

        return (long)jdbc.queryForMap(sql).get("count");
    }

    public String getPage(Long count, Integer currentPage, Integer offset){

        Long currentLong = Long.parseLong(String.valueOf(currentPage));
        Long countPage = 0L;
        //计算所需要的总页数
        if(count%offset == 0){
            countPage = count / offset;
        }else{
            countPage = count / offset + 1;
        }

        StringBuilder sb = new StringBuilder();

        //判断是否为首页
        if(currentPage > 1){
            sb.append("<span class = \"page\"> <a heft=\"?page=" + (currentPage-1));
            sb.append("\"> << </a> </span>");
        }else{
            sb.append("<span class = \"page\"> <a heft=\"?page=1");
            sb.append("\"> << </a> </span>");
        }
        sb.append("<span class=\"page\" style= \"width:50px !important;\">");
        sb.append("<a href=\"page=1\"> start</a> </span>");

        //计算页数导航应该显示的页数
        //剩余页数大于等于5页
        if(countPage - currentLong +1 >= 5){
            for(long i = currentLong; i<currentPage+5; i++){
                sb.append("<span class=\"page\"> <a href=\"?page=" + i);
                sb.append("\">" + i + "</a> </span>");
            }
        }else if(countPage-4>0){
            //若剩余页数不够5页，则判断总页数是否足够填满5页，足够，则显示后5页
            for(long i=countPage-4; i<=countPage; i++){
                sb.append("<span class=\"page\"> <a href=\"?page=" + i);
                sb.append("\">" + i + "</a> </span>");
            }
        }else{
            //最后一种情况为总页数不足5页，则显示所有的页数
            for(long i=1; i<=countPage; i++){
                sb.append("<span class=\"page\"> <a href=\"?page=" + i);
                sb.append("\">" + i + "</a> </span>");
            }
        }
        //页数最少有一页，上文计算总页数时以给countPage赋值
        sb.append("<span class=\"page\" style=\"width: 40px !important;\"> ");
        sb.append("<a href=\"?page= " + countPage);
        sb.append("\"> end</a> </span>");

        //判断是否存在下一页
        if(currentLong < currentPage){
            sb.append("<span class=\"page\"> ");
            sb.append("<a href=\"?page="+currentLong+1);
            sb.append("\"> >> </a> </span>");
        }else{
            sb.append("<span class=\"page\"> ");
            sb.append("<a href=\"?page="+currentLong);
            sb.append("\"> >> </a> </span>");
        }

        sb.append("<span> 共" + countPage + "页</span>");

        return sb.toString();

    }

}
