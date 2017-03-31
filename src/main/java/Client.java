import api.API;
import api.APIResource;
import net.sf.corn.cps.CPScanner;
import net.sf.corn.cps.ClassFilter;
import org.mockserver.client.server.MockServerClient;

import java.util.List;

/**
 * @author fkrauthan
 */
public class Client {

    private MockServerClient mockServerClient;

    public Client() {
        mockServerClient = new MockServerClient("localhost", 8080, "/mockserver-war-3.10.4");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                //proxy.stop();
                mockServerClient.stop();
            }
        });

        init();

    }

    private void init() {
        List<Class<?>> classes = CPScanner.scanClasses(new ClassFilter().packageName("api").annotation(API.class));
        for(Class cls : classes) {
            try {
                System.out.println(cls.getCanonicalName());
                Object obj = cls.newInstance();
                if(obj instanceof APIResource) {
                    initAPI((APIResource)obj);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void initAPI(APIResource apiResource) {
        apiResource.init(mockServerClient);
    }

}
