import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/band-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();//Don't need model for post??
      String bandName = request.queryParams("bandName");
      Band newBand = new Band(bandName);
      newBand.save();
      model.put("band", newBand);
      model.put("template", "templates/band-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      model.put("band", band);
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/:id/venues/new", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      model.put("venues", Venue.all());
      model.put("band", band);
      model.put("template", "templates/band-venues-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id/venues/new", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      int band_id = (Integer.parseInt(request.params("id")));
      String venueName = request.queryParams("venueName");
      Venue newVenue = new Venue(venueName);
      newVenue.save();
      String url = String.format("/bands/%d/venues/new", band_id);
      response.redirect(url);
      return null;
    });

    post("/bands/:id/venues", (request,response) ->{
      //array of values?!? Is that an array of numbers assigned numerically from top to bottom or an array of whatever is in the value attributes fetched upon submit
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      int band_id = (Integer.parseInt(request.params("id")));
      String[] venue_ids =  request.queryParamsValues("venueCheckbox");
        for (String venue_id : venue_ids) {
          Venue newVenue = Venue.find(Integer.parseInt(venue_id));
          band.addVenue(newVenue);
        }
      model.put("venues", band.getVenues());
      model.put("band", band);
      model.put("template", "templates/band-venues-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int bandId = Integer.parseInt (request.params("id"));
      Band band = Band.find(bandId);
      String newBandName = request.queryParams("bandRename");
      band.update(newBandName);
      response.redirect("/bands/" + bandId);
      return null;
    });

    get("/bands/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      model.put("band", band);
      model.put("template", "templates/band-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int bandId = Integer.parseInt (request.params("id"));
      Band band = Band.find(bandId);
      band.delete();
      response.redirect("/bands");
      return null;
    });

    get("/venues/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/venue-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String venueName = request.queryParams("venueName");
      Venue newVenue = new Venue(venueName);
      newVenue.save();
      model.put("venue", newVenue);
      model.put("template", "templates/venue-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("venues", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params("id")));
      model.put("venue", venue);
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:id/bands/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params("id")));
      model.put("bands", Band.all());
      model.put("venue", venue);
      model.put("template", "templates/venues-band-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues/:id/bands/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venue_id = (Integer.parseInt(request.params("id")));
      String bandName = request.queryParams("bandName");
      Band newBand = new Band(bandName);
      newBand.save();
      String url = String.format("/venues/%d/bands/new", venue_id);
      response.redirect(url);
      return null;
    });

    post("/venues/:id/bands", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params("id")));
      int venue_id = (Integer.parseInt(request.params("id")));
      String[] band_ids =  request.queryParamsValues("bandCheckbox");
        for (String band_id : band_ids) {
          Band newBand = Band.find(Integer.parseInt(band_id));
          venue.addBand(newBand);
        }
      model.put("bands", venue.getBands());
      model.put("venue", venue);
      model.put("template", "templates/venue-bands-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());



    post("/venues/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venueId = Integer.parseInt (request.params("id"));
      Venue venue = Venue.find(venueId);
      String newVenueName = request.queryParams("venueRename");
      venue.update(newVenueName);
      response.redirect("/venues/" + venueId);
      return null;
    });

    get("/venues/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params("id")));
      model.put("venue", venue);
      model.put("template", "templates/venue-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venueId = Integer.parseInt (request.params("id"));
      Venue venue = Venue.find(venueId);
      venue.delete();
      response.redirect("/venues");
      return null;
    });
  }
}
