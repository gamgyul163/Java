package Mission01;

import java.io.*;
import java.util.*;

public class Task01 {
    public static void main(String[] args) {
        DOM html = new DOM("html", false);
        HTMLEditor editor = new HTMLEditor(html);

        editor.openTag("head", false);
        editor.addTag("meta",true,"charset","UTF-8");
        editor.openTag("style", false);
        editor.addTag("table",true,"border-collapse","collapse","width","100%");
        editor.addTag("th, td",true,"border","solid 1px #000");
        editor.closeAllTag();

        editor.openTag("body", false);
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

class DOM {
    String name;
    LinkedList<Object> elements;
    HashMap<String, String> attributes;
    boolean isEmptyTag;

    DOM(String name, boolean isEmptyTag) {
        this.name = name;
        this.isEmptyTag = isEmptyTag;
    }
}

class HTMLEditor {
    BufferedWriter writer;
    DOM root;
    DOM cur;

    Stack<DOM> stack = new Stack<>();
    HTMLEditor(DOM root) {
        this.root = root;
        this.cur = root;
    }
    void addElement(Object o) {
        if (cur.elements == null) {
            cur.elements = new LinkedList<>();
        }
        cur.elements.add(o);
    }

    void openTag(String tag, boolean isEmptyTag) {
        stack.push(cur);
        DOM newTag = new DOM(tag, isEmptyTag);
        addElement(newTag);
        cur = newTag;
    }
    void openTag(String tag, boolean isEmptyTag, String ...attribute_value) {
        openTag(tag,isEmptyTag);
        putAttribute(attribute_value);
    }
    void closeTag() {
        cur = stack.pop();
    }
    void closeAllTag() {
        stack.clear();
        cur = root;
    }
    void putAttribute(String... attribute_value) {
        if (cur.attributes == null) {
            cur.attributes = new HashMap<>();
        }
        for (int i = 0; i < attribute_value.length/2; i++) {
            cur.attributes.put(attribute_value[2*i],attribute_value[2*i+1]);
        }
    }

    void addTag(String tag, boolean isEmptyTag, String ...attribute_value) {
        openTag(tag,isEmptyTag, attribute_value);
        closeTag();
    }
    void addHeading(int level, String text) {
        openTag("h"+level,false);
        addElement(text);
        closeTag();
    }

    void addTable(String[] tableHeads, HashMap<String, String> tableDatas) {
        openTag("table",false);
        addTableRow("th", tableHeads);
        for (String key:tableDatas.keySet()) {
            addTableRow("td",new String[]{key,tableDatas.get(key)});
        }
        closeTag();
    }
    void addTableRow(String tag, String[] texts) {
        openTag("tr",false);
        for (String text:texts) {
            openTag(tag,false);
            addElement(text);
            closeTag();
        }
        closeTag();
    }
    void saveAsFile(String fileName) {
        try {
            File file = new File(fileName);
            this.writer = new BufferedWriter(new FileWriter(file));
            this.writer.write(DOMToString(this.root));
            this.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String DOMToString(DOM dom) {
        StringJoiner openTag = new StringJoiner(" ","<",">");
        openTag.add(dom.name);
        if (dom.attributes != null) {
            for (String attribute:dom.attributes.keySet()) {
                openTag.add(String.format("%s=\"%s\"",attribute,dom.attributes.get(attribute)));
            }
        }
        if (dom.isEmptyTag) {
            return openTag.toString();
        }

        StringJoiner result;
        if (!dom.isEmptyTag) {
            result = new StringJoiner("\n");
        } else {
            result = new StringJoiner("");
        }
        result.add(openTag.toString());
        for (Object item: dom.elements) {
            if (item instanceof String) {
                result.add((String)item);
            } else if (dom.name == "style") {
                result.add(CSSToString((DOM)item));
            } else if (item instanceof DOM){
                result.add(DOMToString((DOM)item));
            }
        }
        String closeTag = String.format("</%s>",dom.name);
        result.add(closeTag);
        return result.toString();
    }
    String CSSToString(DOM dom) {
        StringJoiner block = new StringJoiner("; ","{","}");
        for (String property: dom.attributes.keySet()) {
            block.add(String.format("%s: %s;",property,dom.attributes.get(property)));
        }
        return dom.name + block.toString();
    }
}