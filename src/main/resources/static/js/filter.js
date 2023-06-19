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
