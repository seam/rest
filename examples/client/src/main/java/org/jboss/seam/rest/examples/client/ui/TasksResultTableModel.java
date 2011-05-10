package org.jboss.seam.rest.examples.client.ui;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.table.AbstractTableModel;

import org.jboss.seam.rest.examples.client.tasks.SeamTasksAction;

@Singleton
public class TasksResultTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -7931492597081403214L;

    @Inject
    private SeamTasksAction tasksAction;

    @Override
    public int getRowCount() {
        return tasksAction.getResult().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Map<String, String> result = tasksAction.getResult();
        String[][] resultArray = new String[result.size()][2];

        int i = 0;
        for (Map.Entry<String, String> entry : result.entrySet()) {
            resultArray[i] = new String[]{entry.getKey(), entry.getValue()};
            i++;
        }

        return resultArray[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Key";
            case 1:
                return "Value";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
}
