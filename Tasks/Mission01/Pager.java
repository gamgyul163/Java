package Mission01;

import java.util.*;

public class Pager {
    long totalCount; // 전체 게시글 수
    static final int PAGE_SIZE = 10; // 페이지당 보여지는 글의 수
    static final int BLOCK_SIZE = 10; // 페이지 네비게이션에서 보여주는 블럭 수
    Pager(long totalCount) {
        this.totalCount = totalCount;
    }

    String html(long pageIndex) {
        if (pageIndex > getLastIndex()) { // 입력된 현재 페이지가 마지막 페이지보다 크면 마지막 페이지로 가게 한다.
            pageIndex = getLastIndex();
        } else if (pageIndex < 1) { // 입력된 현재 페이지가 0보다 작으면 1페이지로 가게 한다.
            pageIndex = 1;
        }
        long blockStart = ((pageIndex+BLOCK_SIZE-1)/BLOCK_SIZE-1)*BLOCK_SIZE+1;
        long blockEnd = Math.min(blockStart+BLOCK_SIZE-1,getLastIndex());

        StringJoiner result = new StringJoiner("\n");
        result.add("<a href='#'>[처음]</a>");
        result.add("<a href='#'>[이전]</a>");

        for (long i = blockStart; i <= blockEnd ; i++) {
            result.add(String.format("<a href='#'%s>%d</a>",(i == pageIndex)? " class='on'":"",i));
        }

        result.add("<a href='#'>[다음]</a>");
        result.add("<a href='#'>[마지막]</a>");
        return result.toString();
    }
    long getLastIndex() {
        return (totalCount+PAGE_SIZE-1)/PAGE_SIZE;
    }

}
