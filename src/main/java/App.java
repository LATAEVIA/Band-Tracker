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

    post("/bands/:id/venues", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));

      String[] venue_ids =  request.queryParamsValues("venueCheckbox");
      for (String venue_id : venue_ids) {
        Venue newVenue = Venue.find(Integer.parseInt(venue_id));
        band.addVenue(newVenue);
        System.out.println(venue_ids);
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
  }
}
