/*
 * Helper that show/hide filter.
 * Helper assumes only one filter per page.
 */
$(document).ready(function () {
    $("#filter-nav").click(function (event) {
        $("#filter-form").toggle();
    });
});
