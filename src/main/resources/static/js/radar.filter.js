function activationCheckbox() {
    var primary = document.getElementById('primary');
    var filter = document.getElementById('filterByPrimary');
    var checkingExist = document.body.contains(filter);

        // Existence check
        if (checkingExist == true) {
            // Activation condition
            if (filter.checked == true) {
                primary.removeAttribute('disabled');
                }
                else {
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
            }
            else {
            active.setAttribute('disabled', 'disabled');
            }
        }
}
window.onload = activationCheckbox;