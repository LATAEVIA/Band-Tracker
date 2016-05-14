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
      model.put("allVenues", Venue.all());//Add ability to loop through venues to vtl
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

    post("/bands/:id/venues/new/append", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      String[] venue_ids =  request.queryParamsValues("venueCheckbox");
      Band band = Band.find(Integer.parseInt(request.params("id")));
      for (String venue_id : venue_ids) {
        Venue newVenue = Venue.find(Integer.parseInt(venue_id));
        band.addVenue(newVenue);
      }
      model.put("template", "templates/band-venues-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("//bands/:id/venues/new", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      int band_id = (Integer.parseInt(request.params("id")));
      String venueName = request.queryParams("venueName");
      Venue newVenue = new Venue(venueName);
      newVenue.save();
      String url = String.format("/bands/%d/venues/new", band_id);
      response.redirect(url);
      return null;
    });
  }
}
