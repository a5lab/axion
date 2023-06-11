document.addEventListener('DOMContentLoaded', () => {
  function x() {
    $('#filter-nav').click(function ( event ) {
      $('#filter-form').toggle();
    });
  }
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
    var active = document.getElementById('active');
    filter = document.getElementById('filterByActive');
    checkingExist = document.body.contains(filter);

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
  x();
  activationCheckbox();
});

document.addEventListener('DOMContentLoaded', () => {
  ( function () {
    $('#filter-nav').click(function (event) {
      $('#filter-form').toggle();
      });
  });

  if ($('#filterByPrimary').length) {
    if ($('#filterByPrimary')[0].checked){
      $('#primary')[0].removeAttribute('disabled')
    } else {
      $('#primary')[0].setAttribute('disabled', 'disabled')
    }
  }
  if ($('#filterByActive').length) {
    if ($('#filterByActive')[0].checked){
      $('#active')[0].removeAttribute('disabled')
    } else {
      $('#active')[0].setAttribute('disabled', 'disabled')
    }
  }
});