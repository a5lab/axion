$(document).ready(function () {
  $('#filter-nav').click(function (event) {
    $('#filter-form').toggle();
  });
});

$(document).ready(function () {
  if ($('#filterByPrimary').length) {
    if ($('#filterByPrimary').is(':checked')){
      $('#primary').remove('disabled')
    } else {
      $('#primary').attr('disabled', 'disabled')
    }
  }
});

$(document).ready(function () {
  if ($('#filterByActive').length) {
    if ($('#filterByActive').is(':checked')){
      $('#active').remove('disabled')
    } else {
      $('#active').attr('disabled', 'disabled')
    }
  }
});

/*
document.addEventListener('DOMContentLoaded', () => {
  function showFilter() {
    var filterNav = document.getElementById('filter-nav');
    var filterForm = document.getElementById('filter-form');
    (filterNav).click(function ( event ) {
      (filterForm).toggle();
    });
  }
  showFilter();
});

document.addEventListener('DOMContentLoaded', () => {
  function activationCheckbox() {
    var primary = document.getElementById('primary');
    var filter = document.getElementById('filterByPrimary');
    var checkingExist = document.body.contains(filter);

    // Existence check
    if (checkingExist == true) {
       // Activation condition
       if (filter.checked == true) {
         primary.removeAttribute('disabled');
       } else {
         primary.setAttribute('disabled', 'disabled');
       }
    }
  }
  activationCheckbox();
});

document.addEventListener('DOMContentLoaded', () => {
  function activationCheckbox() {
    var active = document.getElementById('active');
    var filter = document.getElementById('filterByActive');
    var checkingExist = document.body.contains(filter);

    // Existence check
    if (checkingExist == true) {
      // Activation condition
      if (filter.checked == true) {
        active.removeAttribute('disabled');
      } else {
        active.setAttribute('disabled', 'disabled');
      }
    }
  }
  activationCheckbox();
});
*/