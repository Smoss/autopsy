/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.keywordsearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import org.sleuthkit.autopsy.corecomponents.DataResultTopComponent;
import org.sleuthkit.autopsy.datamodel.KeyValue;
import org.sleuthkit.autopsy.keywordsearch.KeywordSearch.QueryType;

/**
 * Query manager responsible for running appropriate queries and displaying results
 * for single, multi keyword queries, with detailed or collapsed results
 */
public class KeywordSearchQueryManager {

    public enum Presentation {

        COLLAPSE, DETAIL
    };
    private List<Keyword> queries;
    private Presentation presentation;
    private List<KeywordSearchQuery> queryDelegates;
    private QueryType queryType;
    private static int resultWindowCount = 0; //keep track of unique window ids to display
    private static Logger logger = Logger.getLogger(KeywordSearchQueryManager.class.getName());

    public KeywordSearchQueryManager(List<Keyword> queries, Presentation presentation) {
        this.queries = queries;
        this.presentation = presentation;
        queryType = QueryType.REGEX;
        init();
    }

    public KeywordSearchQueryManager(String query, QueryType qt, Presentation presentation) {
        queries = new ArrayList<Keyword>();
        queries.add(new Keyword(query, qt==QueryType.REGEX?false:true));
        this.presentation = presentation;
        queryType = qt;
        init();
    }

    public KeywordSearchQueryManager(String query, boolean isLiteral, Presentation presentation) {
        queries = new ArrayList<Keyword>();
        queries.add(new Keyword(query, isLiteral));
        this.presentation = presentation;
        queryType = isLiteral?QueryType.WORD:QueryType.REGEX;
        init();
    }

    private void init() {
        queryDelegates = new ArrayList<KeywordSearchQuery>();
        for (Keyword query : queries) {
            KeywordSearchQuery del = null;
            switch (queryType) {
                case WORD:
                    del = new LuceneQuery(query);
                    break;
                case REGEX:
                    if (query.isLiteral()) {
                        del = new LuceneQuery(query);
                    } else {
                        del = new TermComponentQuery(query);
                    }
                    break;
                default:
                    ;
            }
            if (query.isLiteral()) {
                del.escape();
            }
            queryDelegates.add(del);

        }
        //escape();

    }

    public void execute() {
        //execute and present the query
        //delegate query to query objects and presentation child factories
        //if (queryType == QueryType.WORD || presentation == Presentation.DETAIL) {
        //   for (KeywordSearchQuery q : queryDelegates) {
        //       q.execute();
        //  }
        // } else {
        //Collapsed view
        Collection<KeyValueQuery> things = new ArrayList<KeyValueQuery>();
        int queryID = 0;
        for (KeywordSearchQuery q : queryDelegates) {
            Map<String, Object> kvs = new LinkedHashMap<String, Object>();
            final String queryStr = q.getQueryString();
            things.add(new KeyValueQuery(queryStr, kvs, ++queryID, q));
        }

        Node rootNode = null;

        if (things.size() > 0) {
            Children childThingNodes =
                    Children.create(new KeywordSearchResultFactory(queries, things, Presentation.COLLAPSE), true);

            rootNode = new AbstractNode(childThingNodes);
        } else {
            rootNode = Node.EMPTY;
        }

        final String pathText = "Keyword search";
        TopComponent searchResultWin = DataResultTopComponent.createInstance("Keyword search " + (++resultWindowCount), pathText, rootNode, things.size());
        searchResultWin.requestActive();
        // }
    }

    public boolean validate() {
        boolean allValid = true;
        for (KeywordSearchQuery tcq : queryDelegates) {
            if (!tcq.validate()) {
                logger.log(Level.WARNING, "Query has invalid syntax: " + tcq.getQueryString());
                allValid = false;
                break;
            }
        }
        return allValid;
    }
}

/**
 * custom KeyValue that also stores query object  to execute
 */
class KeyValueQuery extends KeyValue {

    private KeywordSearchQuery query;

    KeywordSearchQuery getQuery() {
        return query;
    }

    public KeyValueQuery(String name, Map<String, Object> map, int id, KeywordSearchQuery query) {
        super(name, map, id);
        this.query = query;
    }
}
