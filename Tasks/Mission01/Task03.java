package Mission01;

// 백엔드 19기 임국희

import java.util.*;

public class Task03 {
    private long totalCount; // 전체 게시글 수
    static final int PAGE_SIZE = 10; // 페이지당 보여지는 글의 수
    static final int BLOCK_SIZE = 10; // 페이지 네비게이션에서 보여주는 블럭 수
    Task03(long totalCount) {
        this.totalCount = totalCount;
    }

    String html(long pageIndex) {
        if (pageIndex > getLastIndex()) { // 입력된 현재 페이지가 마지막 페이지보다 크면 마지막 페이지로 가게 한다.
            pageIndex = getLastIndex();
        } else if (pageIndex < 1) { // 입력된 현재 페이지가 0보다 작으면 1페이지로 가게 한다.
            pageIndex = 1;
        }

        StringJoiner html = new StringJoiner("\n");
        html.add(getAnchor("#","","[처음]"));
        html.add(getAnchor("#","","[이전]"));

        for (long i = getBlockStart(pageIndex); i <= getBlockEnd(pageIndex) ; i++) {
            String attribute = "";
            if (pageIndex == i) {
                attribute = "class ='on'";
            }
            html.add(getAnchor("#",attribute,String.valueOf(i)));
        }

        html.add(getAnchor("#","","[다음]"));
        html.add(getAnchor("#","","[마지막]"));

        return html.toString();
    }
    private long getLastIndex() {
        return (totalCount+PAGE_SIZE-1)/PAGE_SIZE;
    }
    private long getBlockStart(long pageIndex) {
        return ((pageIndex+BLOCK_SIZE-1)/BLOCK_SIZE-1)*BLOCK_SIZE+1;
    }
    private long getBlockEnd(long pageIndex) {
        return Math.min(getBlockStart(pageIndex)+BLOCK_SIZE-1,getLastIndex());
    }
    private String getAnchor(String url, String attribute, String btnName) {
        return String.format("<a href='%s'%s>%S</a>",url,attribute,btnName);
    }

}
