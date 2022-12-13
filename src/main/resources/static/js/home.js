//
// Handler for home page

document.addEventListener('DOMContentLoaded', () => {
  // Extract radar properties
  // console.log(  d3.select("h1").text());
  // console.log(  d3.select("p#name").text());
  // console.log(  d3.select("p#description").text());

  // Extract all segments
  let segments = d3.selectAll("table#segments tr.data td.title");
  console.log(  d3.selectAll("table#segments tr.data td.title"));

  segments.each(function (segments, j) {
    // console.log("segment:" + segments[j]);
    // console.log("segment:" + d3.select(this).select("td.title").text());
    // console.log("j" + j);
  });

});

