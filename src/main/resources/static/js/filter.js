/*
 * Helper that show/hide filter.
 * Helper assumes only one filter per page.
 */
$(document).ready(function () {
  $('#filter-nav').click(function (event) {
    $('#filter-form').toggle();
  });
});

/*
 * Helper that show/hide filter on page load.
 * Helper assumes filter activation.
 */
$(document).ready(function () {
  if ($("#title").length) {
    if ($("#website").length) {
      if ($("#title").val().length >= 1 || $("#website").val().length >= 1) {
        $('#filter-form').toggle();
      }
    } else {
      if ($('#filterByPrimary').length || $('#filterByActive').length) {
        if ( $("#title").val().length >= 1 || $('#filterByPrimary').is(':checked') || $('#filterByActive').is(':checked')) {
          $('#filter-form').toggle();
        }
      } else {
        if ($("#title").val().length >= 1) {
          $('#filter-form').toggle();
        }
      }
    }
  }
});

/*
 * Helper checks the status of the filter when the page is loaded.
 * Helper assumes the presence of a filter checkbox on the page.
 */
$(document).ready(function () {
  if ($('#filterByPrimary').length) {
    if ($('#filterByPrimary').is(':checked')){
      $('#primary').remove('disabled')
    } else {
      $('#primary').attr('disabled', 'disabled')
    }
  }
});

/*
 * Helper checks the status of the filter when the page is loaded.
 * Helper assumes the presence of a filter checkbox on the page.
 */
$(document).ready(function () {
  if ($('#filterByActive').length) {
    if ($('#filterByActive').is(':checked')){
      $('#active').remove('disabled')
    } else {
      $('#active').attr('disabled', 'disabled')
    }
  }
});
