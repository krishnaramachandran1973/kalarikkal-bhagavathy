import {Component, OnInit} from '@angular/core';

declare var jQuery: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit
{
  title = 'kalarikkal-bhagavathy';

  ngOnInit(): void
  {
    (function ($)
    {
      $(window)
        .on('load', function ()
        {
          const pre_loader = $('#preloader');
          pre_loader.fadeOut('slow', function ()
          {
            $(this)
              .remove();
          });
        });
    })(jQuery);
  }


  onActivate(event)
  {
    const scrollToTop = window.setInterval(() =>
    {
      const pos = window.pageYOffset;
      if (pos > 0)
      {
        window.scrollTo(0, pos - 20); // how far to scroll on each step
      } else
      {
        window.clearInterval(scrollToTop);
      }
    }, 16);
  }


}
