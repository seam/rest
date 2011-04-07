package org.jboss.seam.rest.examples.client.ui;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.table.AbstractTableModel;

import org.jboss.seam.rest.examples.client.geo.SearchAction;
import org.jboss.seam.rest.examples.client.geo.SearchResult.Location;

@Singleton
public class ZipSearchResultTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -1292943578451876546L;
    @Inject
    private SearchAction search;

    @Override
    public int getRowCount() {
        return search.getLocations().getLocations().size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Location location = search.getLocations().getLocations().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return location.getPostalCode();
            case 1:
                return location.getName();
            case 2:
                return location.getCountryCode();
            case 3:
                return location.getLatitude();
            case 4:
                return location.getLongitude();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Postal code";
            case 1:
                return "Name";
            case 2:
                return "Country";
            case 3:
                return "Latitude";
            case 4:
                return "Longitude";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
}
