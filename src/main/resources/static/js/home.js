//
// Handler for home page

document.addEventListener('DOMContentLoaded', () => {
  // Extract radar properties
  // console.log(  d3.select("h1").text());
  // console.log(  d3.select("p#name").text());
  // console.log(  d3.select("p#description").text());

  // Extract all segments
  let segments = d3.selectAll("table#segments tr.data");

  const arr = Array.from(segments._groups[0]);

  const result = arr.map(function (segment, j) {
    const children = Array.from(segment.children);
    return {
      title: children[0].innerHTML,
      desciption: children[1].innerHTML
    }
  });
  console.log('result>>>>>>>', result)

});