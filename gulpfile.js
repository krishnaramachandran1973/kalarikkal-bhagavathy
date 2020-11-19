const gulp = require('gulp');
const purify = require('gulp-purifycss');

gulp.task('purifyCSS', () => {
  return gulp.src('./dist/kalarikkal-bhagavathy/styles.*.css')
    .pipe(
      purify(
        ['./src/app/**/*.ts',
          './src/app/**/*.html'],
        {
          info: true, // Outputs reduction information (like in the screenshot above)
          minify: true, // Minifies the files after reduction
          rejected: true, // Logs the CSS rules that were removed
          whitelist: ['*transition*', '*animate*', '*dimmer*','label.xng-breadcrumb-trail'] // Ignored css classes
        }
      ),
    )
    .pipe(gulp.dest('./dist/kalarikkal-bhagavathy/'));
});
