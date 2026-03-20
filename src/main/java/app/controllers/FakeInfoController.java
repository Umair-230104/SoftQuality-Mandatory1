package app.controllers;

import app.exception.ApiException;
import app.service.FakeInfoService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeInfoController
{
    private final Logger log = LoggerFactory.getLogger(FakeInfoController.class);

    private final FakeInfoService fakeInfoService;

    public FakeInfoController(FakeInfoService fakeInfoService)
    {
        this.fakeInfoService = fakeInfoService;
    }

    public void getCpr(Context ctx)
    {
        try
        {
            ctx.status(200);
            ctx.json(fakeInfoService.generateCpr());
        } catch (Exception e)
        {
            log.error("500 {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }

    public void getNameGender(Context ctx)
    {
        try
        {
            ctx.status(200);
            ctx.json(fakeInfoService.generateNameGender());
        } catch (Exception e)
        {
            log.error("500 {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }

    public void getNameGenderDob(Context ctx)
    {
        try
        {
            ctx.status(200);
            ctx.json(fakeInfoService.generateNameGenderDob());
        } catch (Exception e)
        {
            log.error("500 {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }

    public void getCprNameGender(Context ctx)
    {
        try
        {
            ctx.status(200);
            ctx.json(fakeInfoService.generateCprNameGender());
        } catch (Exception e)
        {
            log.error("500 {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }

    public void getCprNameGenderDob(Context ctx)
    {
        try
        {
            ctx.status(200);
            ctx.json(fakeInfoService.generateCprNameGenderDob());
        } catch (Exception e)
        {
            log.error("500 {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }

    public void getAddress(Context ctx)
    {
        try
        {
            ctx.status(200);
            ctx.json(fakeInfoService.generateAddress());
        } catch (Exception e)
        {
            log.error("500 {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }

    public void getPhoneNumber(Context ctx)
    {
        try
        {
            ctx.status(200);
            ctx.json(fakeInfoService.generatePhoneNumber());
        } catch (Exception e)
        {
            log.error("500 {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }

    public void getPerson(Context ctx)
    {
        try
        {
            ctx.status(200);
            ctx.json(fakeInfoService.generatePerson());
        } catch (Exception e)
        {
            log.error("500 {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }

    public void getPeople(Context ctx)
    {
        try
        {
            int count = Integer.parseInt(ctx.pathParam("count"));

            ctx.status(200);
            ctx.json(fakeInfoService.generatePeople(count));
        } catch (NumberFormatException e)
        {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, "Count must be a valid number");
        } catch (IllegalArgumentException e)
        {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        } catch (Exception e)
        {
            log.error("500 {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }
}