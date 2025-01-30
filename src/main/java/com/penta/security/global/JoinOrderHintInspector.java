package com.penta.security.global;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public class JoinOrderHintInspector implements StatementInspector {

    private static final String TARGET = "/\\*\\s*JOIN_ORDER\\((.*?)\\)\\s*\\*/";

    private static final Pattern PATTERN = Pattern.compile(TARGET, Pattern.CASE_INSENSITIVE);

    @Override
    public String inspect(String sql) {
        Matcher matcher = PATTERN.matcher(sql);
        if (matcher.find()) {
            String group = matcher.group(1);
            return sql.replaceAll("(.*?)select", "select /*+ JOIN_ORDER(" + group + ") */");
        }
        return sql;
    }
}
