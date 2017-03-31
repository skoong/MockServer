package api;

import org.mockserver.client.server.MockServerClient;

/**
 * @author fkrauthan
 */
public interface APIResource {

    void init(MockServerClient mockServerClient);

}
