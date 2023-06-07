function x() {
    var checkbox = document.getElementById('primary');
    var checkbox1 = document.getElementById('filterByPrimary');
    var check = document.body.contains(checkbox1);
        if (check == true) {
            if (checkbox1.checked == true) {
                checkbox.removeAttribute('disabled');
                }
                else {
                checkbox.setAttribute('disabled', 'disabled');
                }
        }
    checkbox = document.getElementById('active');
    checkbox1 = document.getElementById('filterByActive');
    var check = document.body.contains(checkbox1);
        if (check == true) {
            if (checkbox1.checked == true) {
            checkbox.removeAttribute('disabled');
            }
            else {
            checkbox.setAttribute('disabled', 'disabled');
            }
        }
}
window.onload = x;