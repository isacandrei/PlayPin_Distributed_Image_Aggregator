package controllers

import javax.inject._

import play.api.mvc._

import scala.concurrent.{ExecutionContext}
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (implicit exec: ExecutionContext) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

    val imgs = Array(
        "http://www.freedigitalphotos.net/images/img/homepage/87357.jpg",
        "http://121clicks.com/wp-content/uploads/2012/04/portrait_eyes_23.jpg",
        "http://cssdeck.com/uploads/media/items/6/6f3nXse.png",
        "http://cssdeck.com/uploads/media/items/2/2v3VhAp.png",
        "https://farm1.staticflickr.com/542/19074222720_ca5640fc18_b.jpg",
        "http://www.freedigitalphotos.net/images/img/homepage/87357.jpg",
        "http://cssdeck.com/uploads/media/items/2/2v3VhAp.png",
        "http://cssdeck.com/uploads/media/items/1/1swi3Qy.png",
        "http://121clicks.com/wp-content/uploads/2012/04/portrait_eyes_23.jpg",
        "http://vanimg.s3.amazonaws.com/portrait-20.jpg",
        "http://www.freedigitalphotos.net/images/img/homepage/87357.jpg",
        "http://121clicks.com/wp-content/uploads/2012/04/portrait_eyes_23.jpg",
        "http://cssdeck.com/uploads/media/items/6/6f3nXse.png",
        "http://cssdeck.com/uploads/media/items/2/2v3VhAp.png",
        "https://farm1.staticflickr.com/542/19074222720_ca5640fc18_b.jpg",
        "http://www.freedigitalphotos.net/images/img/homepage/87357.jpg",
        "http://cssdeck.com/uploads/media/items/2/2v3VhAp.png",
        "http://cssdeck.com/uploads/media/items/1/1swi3Qy.png",
        "http://121clicks.com/wp-content/uploads/2012/04/portrait_eyes_23.jpg",
        "http://vanimg.s3.amazonaws.com/portrait-20.jpg",
        "http://www.freedigitalphotos.net/images/img/homepage/87357.jpg",
        "http://121clicks.com/wp-content/uploads/2012/04/portrait_eyes_23.jpg",
        "http://cssdeck.com/uploads/media/items/6/6f3nXse.png",
        "http://cssdeck.com/uploads/media/items/2/2v3VhAp.png",
        "https://farm1.staticflickr.com/542/19074222720_ca5640fc18_b.jpg",
        "http://www.freedigitalphotos.net/images/img/homepage/87357.jpg",
        "http://cssdeck.com/uploads/media/items/2/2v3VhAp.png",
        "http://cssdeck.com/uploads/media/items/1/1swi3Qy.png",
        "http://121clicks.com/wp-content/uploads/2012/04/portrait_eyes_23.jpg",
        "http://vanimg.s3.amazonaws.com/portrait-20.jpg",
        "http://121clicks.com/wp-content/uploads/2012/04/portrait_eyes_23.jpg")

    def home = Action {
        Ok(views.html.home(imgs))
    }

}

