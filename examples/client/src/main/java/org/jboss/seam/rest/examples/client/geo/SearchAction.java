package org.jboss.seam.rest.examples.client.geo;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.solder.logging.Logger;
import org.jboss.seam.rest.client.RestClient;
import org.jboss.seam.rest.examples.client.ConnectionException;
import org.jboss.seam.rest.examples.client.StatusException;

/**
 * Executes a zip code query using the geonames API. {@link http://www.geonames.org/export/ws-overview.html}
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Singleton
public class SearchAction {
    @Inject
    @RestClient("http://api.geonames.org")
    private GeonamesService api;

    private static final Logger log = Logger.getLogger(SearchAction.class);

    private static final String ZIP_PATTERN = "^\\d{5}([\\-]\\d{4})?$";
    private SearchResult locations = new SearchResult();

    public void search(String query) throws ConnectionException {
        if (!query.matches(ZIP_PATTERN)) {
            throw new IllegalArgumentException();
        }

        try {
            locations = api.searchZip(query, 20, "demo");

            if (locations.getStatus() != null) {
                log.error(locations.getStatus().getMessage());
                throw new StatusException(locations.getStatus().getMessage());
            }
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new ConnectionException(e);
        }
    }

    public SearchResult getLocations() {
        return locations;
    }
}
