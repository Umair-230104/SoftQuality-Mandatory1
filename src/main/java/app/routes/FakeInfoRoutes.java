package app.routes;

import app.config.HibernateConfig;
import app.controllers.FakeInfoController;
import app.daos.PostalCodeDAO;
import app.security.enums.Role;
import app.service.FakeInfoService;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.get;

public class FakeInfoRoutes
{
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("fakeinfodb");

    private final PostalCodeDAO postalCodeDAO = new PostalCodeDAO(emf);
    private final FakeInfoService fakeInfoService = new FakeInfoService(postalCodeDAO);
    private final FakeInfoController fakeInfoController = new FakeInfoController(fakeInfoService);

    public EndpointGroup getFakeInfoRoutes()
    {
        return () ->
        {
            get("/cpr", fakeInfoController::getCpr, Role.ANYONE);
            get("/name-gender", fakeInfoController::getNameGender, Role.ANYONE);
            get("/name-gender-dob", fakeInfoController::getNameGenderDob, Role.ANYONE);
            get("/cpr-name-gender", fakeInfoController::getCprNameGender, Role.ANYONE);
            get("/cpr-name-gender-dob", fakeInfoController::getCprNameGenderDob, Role.ANYONE);
            get("/address", fakeInfoController::getAddress, Role.ANYONE);
            get("/phone", fakeInfoController::getPhoneNumber, Role.ANYONE);
            get("/person", fakeInfoController::getPerson, Role.ANYONE);
            get("/persons/{count}", fakeInfoController::getPeople, Role.ANYONE);
        };
    }
}