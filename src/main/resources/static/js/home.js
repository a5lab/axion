//
// Handler for home page

document.addEventListener('DOMContentLoaded', () => {

  // Radar configuration
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
  console.log('radar configuration is:', config)

  // Extract radar configuration
  let radar_name = d3.selectAll("span#name").text();
  let radar_description = d3.selectAll("span#description").text();
  console.log('radar name is:', radar_name)
  console.log('radar description is:', radar_description)

  // Extract all segments
  let html_segments = d3.selectAll("table#segments tbody tr");
  const segments = Array.from(html_segments._groups[0]).map(function (segment, j) {
    const children = Array.from(segment.children);
    return {
      title: children[0].innerHTML,
      desciption: children[1].innerHTML
    }
  });
  console.log('segments are:', segments)

  // Extract all rings
  let html_rings = d3.selectAll("table#rings tbody tr");
  const rings = Array.from(html_rings._groups[0]).map(function (ring, j) {
    const children = Array.from(ring.children);
    return {
      title: children[0].innerHTML,
      desciption: children[1].innerHTML,
      priority: children[2].innerHTML
    }
  });
  console.log('rings are:', rings)

  // Extract all blips
  let html_blips = d3.selectAll("table#blips tbody tr");
  const blips = Array.from(html_blips._groups[0]).map(function (blip, j) {
    const children = Array.from(blip.children);
    return {
      title: children[0].innerHTML,
      ring: children[1].innerHTML,
      segment: children[2].innerHTML,
      description: children[3].innerHTML
    }
  });
  console.log('blips are:', blips)

});