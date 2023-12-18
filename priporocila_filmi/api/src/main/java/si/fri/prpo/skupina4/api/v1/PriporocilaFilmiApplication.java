package si.fri.prpo.skupina4.api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
@OpenAPIDefinition(
                info=@Info(title="Priporočila filmi API", version = "v1",
                contact = @Contact(email="prpo@fri.uni-lj.si"),
                license = @License(name = "Apache 2.0"), description = "API za storitev filmi"),
                servers = @Server(url="http://localhost:8080/"))

@ApplicationPath("v1")
public class PriporocilaFilmiApplication extends Application {


}
