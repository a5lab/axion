//
// Handler for home page

document.addEventListener('DOMContentLoaded', () => {
  let config = {
    svg_id: "radar",
    width: 1450,
    height: 1000,
    colors: {
      background: "#fff",
      grid: "#bbb",
      inactive: "#ddd"
    }
  }


  // Extract all segments
  let html_segments = d3.selectAll("table#segments tbody tr");
  const segments = Array.from(html_segments._groups[0]).map(function (segment, j) {
    const children = Array.from(segment.children);
    return {
      title: children[0].innerHTML,
      desciption: children[1].innerHTML
    }
  });
  console.log('segmets are:', segments)

});