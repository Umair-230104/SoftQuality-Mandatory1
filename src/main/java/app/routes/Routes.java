package app.routes;


import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes
{


    private final FakeInfoRoutes fakeInfoRoutes = new FakeInfoRoutes();

    public EndpointGroup getApiRoutes()
    {
        return () ->
        {
            path("/fakeinfo", fakeInfoRoutes.getFakeInfoRoutes());
        };
    }
}
