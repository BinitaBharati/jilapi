/**
 * 
 * @author binita.bharati@gmail.com
 * Applicable when the command parser is not Tabular.ie is applicable when parser type is Nested/Chunked.
 * 
 * Any single and smallest unit of meaningful data is termed as entity.All implementations of entity
 * parsers are required to implement this interface.
 *
 *
 */

package com.github.binitabharati.jilapi.entity.parser;

import java.util.Map;

public interface EntityParser {
    
    /**
     * 
     * @param input
     * @return - Map<String,Object> - Object type is selected as the value part for the Map,
     * so as to be able to cater to any Object. List, Map, Arrays are also subclass of Object.
     */
    Map<String, Object> parse(String input);

}
