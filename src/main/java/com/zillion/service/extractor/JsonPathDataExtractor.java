package com.zillion.service.extractor;

import java.io.IOException;
import java.util.List;

/**
 * Created by Konstantin on 4/28/16.
 */
public interface JsonPathDataExtractor {
    List<String> extractInventorFullName(String jsonString) throws IOException;
}
