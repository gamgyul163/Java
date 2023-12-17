package Mission01;

// 백엔드 19기 임국희

import java.io.*;
import java.util.*;

public class Task01 {
    public static void main(String[] args) {
        Dom html = new Dom("html", false);
        HtmlEditor editor = new HtmlEditor(html);

        editor.openTag("head");
        editor.addEmptyTag("meta","charset","UTF-8");
        editor.openTag("style");
        editor.addEmptyTag("table","border-collapse","collapse","width","100%");
        editor.addEmptyTag("th, td","border","solid 1px #000");
        editor.closeAllTag();

        editor.openTag("body");
        editor.addHeading(1,"자바 환경정보");

        HashMap<String,String> javaProperties = new HashMap<>();
        for (Object k:System.getProperties().keySet()) {
            javaProperties.put(k.toString(),System.getProperty(k.toString()));
        }
        editor.addTable(new String[]{"키",""},javaProperties);
        editor.closeAllTag();


        editor.saveAsFile("property.html");
    }
}

class Dom { // 문서의 정보를 저장하고 있는 클래스
    private String name;
    private LinkedList<Object> elements; // 하위 요소
    private HashMap<String, String> attributes; // 속성
    private boolean isEmptyTag; // 빈태그 여부

    Dom(String name, boolean isEmptyTag) {
        this.name = name;
        this.isEmptyTag = isEmptyTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Object> getElements() {
        return elements;
    }

    public void setElements(LinkedList<Object> elements) {
        this.elements = elements;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public boolean isEmptyTag() {
        return isEmptyTag;
    }

    public void setEmptyTag(boolean emptyTag) {
        isEmptyTag = emptyTag;
    }
}

class HtmlEditor { // 문서 편집기
    private Dom root; // 현재 편집 중인 문서의 최상위 레벨
    private Dom cur; // 현재 편집 중인 레벨

    private Stack<Dom> stack = new Stack<>(); // 커서 이동을 위한 스택
    HtmlEditor(Dom root) {
        this.root = root;
        this.cur = root;
    }
    void addElement(Object o) { // 현재 레벨의 하위에 요소 추가
        if (cur.getElements() == null) {
            cur.setElements(new LinkedList<>());
        }
        cur.getElements().add(o);
    }
    void openTag(String tag) {
        openTag(tag, false);
    }

    void openTag(String tag, boolean isEmptyTag) { // 하위 레벨을 추가하고 새로 추가한 레벨로 커서 이동
        stack.push(cur);
        Dom newTag = new Dom(tag, isEmptyTag);
        addElement(newTag);
        cur = newTag;
    }
    void openTag(String tag, boolean isEmptyTag, String ...attribute_value) { // 추가+속성 설정
        openTag(tag,isEmptyTag);
        putAttribute(attribute_value);
    }
    void closeTag() {
        cur = stack.pop();
    } // 현재 레벨 편집을 종료하고 상위로 이동
    void closeAllTag() { // 최상위 레벨로 이동
        stack.clear();
        cur = root;
    }
    void putAttribute(String... attribute_value) { // 현재 레벨에 속성 추가
        if (cur.getAttributes() == null) {
            cur.setAttributes(new HashMap<>());
        }
        for (int i = 0; i < attribute_value.length/2; i++) {
            cur.getAttributes().put(attribute_value[2*i],attribute_value[2*i+1]);
        }
    }

    void addEmptyTag(String tag, String ...attribute_value) { // 커서는 이동 시키지 않고 바로 하위에 태그 추가
        openTag(tag,true, attribute_value);
        closeTag();
    }
    void addHeading(int level, String text) { // 제목 요소 추가
        openTag("h"+level,false);
        addElement(text);
        closeTag();
    }

    void addTable(String[] tableHeads, HashMap<String, String> tableDatas) { // 테이블 요소 추가
        openTag("table",false);
        addTableRow("th", tableHeads);
        for (String key:tableDatas.keySet()) {
            addTableRow("td",new String[]{key,tableDatas.get(key)});
        }
        closeTag();
    }
    void addTableRow(String tag, String[] texts) { // 테이블 데이터 추가
        openTag("tr",false);
        for (String text:texts) {
            openTag(tag,false);
            addElement(text);
            closeTag();
        }
        closeTag();
    }
    void saveAsFile(String fileName) { // 파일로 저장하기
        try {
            File file = new File(fileName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("<!DOCTYPE html>\n");
            writer.write(domToString(this.root));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String domToString(Dom dom) { // DOM을 문자열로 변경
        StringJoiner openTag = new StringJoiner(" ","<",">");
        openTag.add(dom.getName());

        if (dom.getAttributes() != null) { // 속성 적용
            for (String attribute:dom.getAttributes().keySet()) {
                openTag.add(String.format("%s=\"%s\"",attribute,dom.getAttributes().get(attribute)));
            }
        }
        if (dom.isEmptyTag()) { // 빈 태그면 속성 적용 후 바로 반환
            return openTag.toString();
        }

        StringJoiner result = new StringJoiner("\n"); // 하위 요소들을 받는 역할

        result.add(openTag.toString()); // 여는 태그 저장

        for (Object item: dom.getElements()) { // 하위 요소 저장
            if (item instanceof String) { // 요소가 문자열이면 바로 넣는다.
                result.add((String)item);
            } else if (dom.getName().equals("style")) { // 스타일 태그의 요소들은 별도로 처리한다.
                result.add(csstostring((Dom)item));
            } else if (item instanceof Dom){ // 요소가 DOM이면 재귀로 넣는다.
                result.add(domToString((Dom)item));
            }
        }

        String closeTag = String.format("</%s>",dom.getName());
        result.add(closeTag); // 닫는 태그 저장

        return result.toString();
    }
    private String csstostring(Dom dom) { // CSS를 문자열로 변경
        StringJoiner block = new StringJoiner("; ","{","}");

        for (String property: dom.getAttributes().keySet()) {
            block.add(String.format("%s: %s;",property,dom.getAttributes().get(property)));
        }
        return dom.getName() + block.toString();
    }
}