/*
 * Helper that show/hide filter.
 * Helper assumes only one filter per page.
 */
$(document).ready(function () {
    $("#filter-nav").click(function (event) {
        $("#filter-form").toggle();
    });
});

/* todo: remove this code
document.querySelectorAll('.js-submit-confirm').forEach(($item) => {
    $item.addEventListener('submit', (event) => {
        if (!confirm(event.currentTarget.getAttribute('data-confirm-message'))) {
            event.preventDefault();
            return false;
        }
        return true;
    });
});
*/