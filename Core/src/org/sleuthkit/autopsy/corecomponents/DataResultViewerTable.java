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
package org.sleuthkit.autopsy.corecomponents;

import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.dnd.DnDConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.sleuthkit.autopsy.coreutils.Logger;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import org.netbeans.swing.outline.DefaultOutlineModel;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;
import org.openide.nodes.Node.PropertySet;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.ServiceProvider;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataResultViewer;

/**
 * DataResult sortable table viewer
 */
@ServiceProvider(service = DataResultViewer.class)
public class DataResultViewerTable extends AbstractDataResultViewer {

    private String firstColumnLabel = "Name";
    private Set<Property> propertiesAcc = new LinkedHashSet<Property>();
    private static final Logger logger = Logger.getLogger(DataResultViewerTable.class.getName());

    /**
     * Creates new form DataResultViewerTable
     */
    public DataResultViewerTable() {
        initComponents();

        OutlineView ov = ((OutlineView) this.tableScrollPanel);
        ov.setAllowedDragActions(DnDConstants.ACTION_NONE);
        ov.setAllowedDropActions(DnDConstants.ACTION_NONE );

        // only allow one item to be selected at a time
        ov.getOutline().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // don't show the root node
        ov.getOutline().setRootVisible(false);
        ov.getOutline().setDragEnabled(false);
    }

    /**
     * Expand node
     *
     * @param n Node to expand
     */
    @Override
    public void expandNode(Node n) {
        super.expandNode(n);

        if (this.tableScrollPanel != null) {
            OutlineView ov = ((OutlineView) this.tableScrollPanel);
            ov.expandNode(n);
        }
    }

    @Override
    public void nodeSelected(Node selectedNode) {

    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableScrollPanel = new OutlineView(this.firstColumnLabel);

        //new TreeTableView()
        tableScrollPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                tableScrollPanelComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableScrollPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tableScrollPanelComponentResized
    }//GEN-LAST:event_tableScrollPanelComponentResized
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane tableScrollPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets regular Bean property set properties from first child of Node.
     *
     * @param parent Node with at least one child to get properties from
     * @return Properties,
     */
    private Node.Property[] getChildPropertyHeaders(Node parent) {
        Node firstChild = parent.getChildren().getNodeAt(0);

        if (firstChild == null) {
            throw new IllegalArgumentException("Couldn't get a child Node from the given parent.");
        } else {
            for (PropertySet ps : firstChild.getPropertySets()) {
                if (ps.getName().equals(Sheet.PROPERTIES)) {
                    return ps.getProperties();
                }
            }

            throw new IllegalArgumentException("Child Node doesn't have the regular PropertySet.");
        }
    }

    /**
     * Gets regular Bean property set properties from all first children and,
     * recursively, subchildren of Node. Note: won't work out the box for lazy
     * load - you need to set all children props for the parent by hand
     *
     * @param parent Node with at least one child to get properties from
     * @return Properties,
     */
    private Node.Property[] getAllChildPropertyHeaders(Node parent) {
        Node firstChild = parent.getChildren().getNodeAt(0);

        Property[] properties = null;

        if (firstChild == null) {
            throw new IllegalArgumentException("Couldn't get a child Node from the given parent.");
        } else {
            Set<Property> allProperties = new LinkedHashSet<Property>();
            while (firstChild != null) {
                for (PropertySet ps : firstChild.getPropertySets()) {
                    //if (ps.getName().equals(Sheet.PROPERTIES)) {
                    //return ps.getProperties();
                    final Property[] props = ps.getProperties();
                    final int propsNum = props.length;
                    for (int i = 0; i < propsNum; ++i) {
                        allProperties.add(props[i]);
                    }
                    //}
                }
                firstChild = firstChild.getChildren().getNodeAt(0);
            }

            properties = allProperties.toArray(new Property[0]);
            //throw new IllegalArgumentException("Child Node doesn't have the regular PropertySet.");
        }
        return properties;

    }

    /**
     * Gets regular Bean property set properties from all children and,
     * recursively, subchildren of Node. Note: won't work out the box for lazy
     * load - you need to set all children props for the parent by hand
     *
     * @param parent Node with at least one child to get properties from
     * @param rows max number of rows to retrieve properties for (can be used
     * for memory optimization)
     */
    private void getAllChildPropertyHeadersRec(Node parent, int rows) {
        Children children = parent.getChildren();
        int total = Math.min(rows, children.getNodesCount());
        for (int i = 0; i < total; i++) {
            Node child = children.getNodeAt(i);
            for (PropertySet ps : child.getPropertySets()) {
                //if (ps.getName().equals(Sheet.PROPERTIES)) {
                //return ps.getProperties();
                final Property[] props = ps.getProperties();
                final int propsNum = props.length;
                for (int j = 0; j < propsNum; ++j) {
                    propertiesAcc.add(props[j]);
                }
                //}
            }
            getAllChildPropertyHeadersRec(child, rows);
        }
    }

    @Override
    public boolean isSupported(Node selectedNode) {
        return true;
    }

    @Override
    public void setNode(Node selectedNode) {
        // change the cursor to "waiting cursor" for this operation
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            boolean hasChildren = false;


            if (selectedNode != null) {
                hasChildren = selectedNode.getChildren().getNodesCount() > 0;
            }


            // if there's no selection node, do nothing
            if (hasChildren) {
                Node root = selectedNode;

                //wrap to filter out children
                //note: this breaks the tree view mode in this generic viewer,
                //so wrap nodes earlier if want 1 level view
                //if (!(root instanceof TableFilterNode)) {
                ///    root = new TableFilterNode(root, true);
                //}

                em.setRootContext(root);


                OutlineView ov = ((OutlineView) this.tableScrollPanel);

                propertiesAcc.clear();

                this.getAllChildPropertyHeadersRec(selectedNode, 100);
                List<Node.Property> props = new ArrayList<Node.Property>(propertiesAcc);
                if (props.size() > 0) {
                    Node.Property prop = props.remove(0);
                    ((DefaultOutlineModel) ov.getOutline().getOutlineModel()).setNodesColumnLabel(prop.getDisplayName());
                }


                // *********** Make the TreeTableView to be sortable ***************

                //First property column is sortable, but also sorted initially, so
                //initially this one will have the arrow icon:
                if (props.size() > 0) {
                    props.get(0).setValue("TreeColumnTTV", Boolean.TRUE); // Identifies special property representing first (tree) column.
                    props.get(0).setValue("SortingColumnTTV", Boolean.TRUE); // TreeTableView should be initially sorted by this property column.
                }

                // The rest of the columns are sortable, but not initially sorted,
                // so initially will have no arrow icon:
                String[] propStrings = new String[props.size() * 2];
                for (int i = 0; i < props.size(); i++) {
                    props.get(i).setValue("ComparableColumnTTV", Boolean.TRUE);
                    propStrings[2 * i] = props.get(i).getName();
                    propStrings[2 * i + 1] = props.get(i).getDisplayName();
                }

                ov.setPropertyColumns(propStrings);
                // *****************************************************************

                //            // set the first entry
                //            Children test = root.getChildren();
                //            Node firstEntryNode = test.getNodeAt(0);
                //            try {
                //                this.getExplorerManager().setSelectedNodes(new Node[]{firstEntryNode});
                //            } catch (PropertyVetoException ex) {}


                // show the horizontal scroll panel and show all the content & header

                int totalColumns = props.size();

                //int scrollWidth = ttv.getWidth();
                int margin = 4;
                int startColumn = 1;
                ov.getOutline().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                // get the fontmetrics
                //FontMetrics metrics = ttv.getGraphics().getFontMetrics();
                FontMetrics metrics = ov.getGraphics().getFontMetrics();

                // get first 100 rows values for the table
                Object[][] content = null;
                content = getRowValues(selectedNode, 100);


                if (content != null) {
                    // for the "Name" column
                    int nodeColWidth = Math.min(getMaxColumnWidth(0, metrics, margin, 40, firstColumnLabel, content), 250); // Note: 40 is the width of the icon + node lines. Change this value if those values change!
                    ov.getOutline().getColumnModel().getColumn(0).setPreferredWidth(nodeColWidth);

                    // get the max for each other column
                    for (int colIndex = startColumn; colIndex <= totalColumns; colIndex++) {
                        int colWidth = Math.min(getMaxColumnWidth(colIndex, metrics, margin, 8, props, content), 350);
                        ov.getOutline().getColumnModel().getColumn(colIndex).setPreferredWidth(colWidth);
                    }
                }

                // if there's no content just auto resize all columns
                if (!(content.length > 0)) {
                    // turn on the auto resize
                    ov.getOutline().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                }

            } else {
                Node emptyNode = new AbstractNode(Children.LEAF);
                em.setRootContext(emptyNode); // make empty node
                ((OutlineView) this.tableScrollPanel).getOutline().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                ((OutlineView) this.tableScrollPanel).setPropertyColumns(); // set the empty property header
            }
        } finally {
            this.setCursor(null);
        }
    }

    private static Object[][] getRowValues(Node node, int rows) {
        // how many rows are we returning
        int maxRows = Math.min(rows, node.getChildren().getNodesCount());

        Object[][] objs = new Object[maxRows][];

        for (int i = 0; i < maxRows; i++) {
            PropertySet[] props = node.getChildren().getNodeAt(i).getPropertySets();
            if (props.length == 0) //rare special case
            {
                continue;
            }
            Property[] property = props[0].getProperties();
            objs[i] = new Object[property.length];


            for (int j = 0; j < property.length; j++) {
                try {
                    objs[i][j] = property[j].getValue();
                } catch (IllegalAccessException ignore) {
                    objs[i][j] = "n/a";
                } catch (InvocationTargetException ignore) {
                    objs[i][j] = "n/a";
                }
            }
        }
        return objs;
    }

    @Override
    public String getTitle() {
        return "Table View";
    }

    @Override
    public DataResultViewer getInstance() {
        return new DataResultViewerTable();
    }

    /**
     * Gets the max width of the column from the given index, header, and table.
     *
     * @param index the index of the column on the table / header
     * @param metrics the font metrics that this component use
     * @param margin the left/right margin of the column
     * @param padding the left/right padding of the column
     * @param header the property headers of the table
     * @param table the object table
     * @return max the maximum width of the column
     */
    private int getMaxColumnWidth(int index, FontMetrics metrics, int margin, int padding, List<Node.Property> header, Object[][] table) {
        // set the tree (the node / names column) width
        String headerName = header.get(index - 1).getDisplayName();

        return getMaxColumnWidth(index, metrics, margin, padding, headerName, table);
    }

    /**
     * Gets the max width of the column from the given index, header, and table.
     *
     * @param index the index of the column on the table / header
     * @param metrics the font metrics that this component use
     * @param margin the left/right margin of the column
     * @param padding the left/right padding of the column
     * @param header the column header for the comparison
     * @param table the object table
     * @return max the maximum width of the column
     */
    private synchronized int getMaxColumnWidth(int index, FontMetrics metrics, int margin, int padding, String header, Object[][] table) {
        // set the tree (the node / names column) width
        String headerName = header;
        int headerWidth = metrics.stringWidth(headerName); // length of the header
        int colWidth = 0;

        // Get maximum width of column data
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null || index >= table[i].length) {
                continue;
            }
            String test = table[i][index].toString();
            colWidth = Math.max(colWidth, metrics.stringWidth(test));
        }

        colWidth += padding; // add the padding on the most left gap
        headerWidth += 8; // add the padding to the header (change this value if the header padding value is changed)

        // Set the width
        int width = Math.max(headerWidth, colWidth);
        width += 2 * margin; // Add margin

        return width;
    }

    @Override
    public void clearComponent() {
        this.tableScrollPanel.removeAll();
        this.tableScrollPanel = null;

        //this destroys em
        super.clearComponent();

    }

  
}
