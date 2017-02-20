package hello;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

@RestController
public class GreetingController {

    private static String template = "Hi Hello Welcome, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
	private ListRepository repository;

	@CrossOrigin
	@RequestMapping("/lists")
    //public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	System.out.println("/lists ----------?");
    	
    	String contents = "<tr><td><b>EntityId</b></td><td><b>Owner</b></td><td><b>Last Updated</b></td></tr>";
	    for (WatchList list : repository.findAll()) {
			contents = contents + "<tr><td><a href=# target='_blank' onclick=window.open('/listDetails.html','mywin','width=500,height=200'); >" + list.listName +"</a></td>"
			+"<td>"+list.listOwner+"</td><td>01-13-2017</td></tr>";
		}

        return "<table border='1' width='100%'>"+contents+"</table><table>"+
        "<tr><td><a href=# target='_blank' onclick=window.open('/createpage','mywin','width=500,height=200'); >Create List</a></td></tr>"+
        "<tr><td><a href='/deleteall'>Delete All</a></td></tr></table>";
    }

    @CrossOrigin
	@RequestMapping("/lists/json")
    //public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    public String lists(@RequestParam(value="name", defaultValue="World") String name) {
    	System.out.println("/lists/json ----------?");
    	
    	/*String contents = "<tr><td><b>List Name</b></td><td><b>Type</b></td><td><b>List Type</b></td>"
		+"<td><b>Owner</b></td><td><b>Last Updated</b></td><td><b>Access Level</b></td></tr>";*/
		String lists = "{\"lists\": [";
	    for (WatchList list : repository.findAll()) {
			lists = lists + "{\"name\":\""+list.listName+"\", \"type\":\""+list.listType+"\", \"owner\":\""+list.listOwner+"\"},";
		}
		lists = lists + "{}]}";
        /*return "<table border='1' width='100%'>"+contents+"</table><table>"+
        "<tr><td><a href=# target='_blank' onclick=window.open('/createpage','mywin','width=500,height=200'); >Create List</a></td></tr>"+
        "<tr><td><a href='/deleteall'>Delete All</a></td></tr></table>";*/

        return lists;
    }

    @RequestMapping(value = "/createpage", method = RequestMethod.GET)
    public ModelAndView createPage(Locale locale, Model model) {
    	System.out.println("in /static/html");
        return new ModelAndView("/createList.html"); // NOTE here there is /someurl/resources
    }

    @RequestMapping("/createlist")
    public ModelAndView create(@RequestParam(value="listname") String listname, 
    						   @RequestParam(value="listtype") String listtype,
    						   @RequestParam(value="listowner") String listowner) {

    	System.out.println("Input param - "+listname +" - "+listtype+" - "+listowner);

		repository.save(new WatchList(listname, listtype, listowner));
        return new ModelAndView("/close.html");
    }

    @RequestMapping(value = "/deleteall", method = RequestMethod.GET)
    public ModelAndView deleteAll(Locale locale, Model model) {
    	//System.out.println("in /static/html");
    	repository.deleteAll();
        return new ModelAndView("/lists"); // NOTE here there is /someurl/resources
    }
}
