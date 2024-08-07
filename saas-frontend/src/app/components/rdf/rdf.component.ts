import { Component, OnInit } from '@angular/core';
import { RDFService } from '../../services/rdf-service.service';

@Component({
  selector: 'app-rdf',
  templateUrl: './rdf.component.html',
  styleUrls: ['./rdf.component.scss']
})
export class RDFComponent implements OnInit {
  rdfData: string = '';
  errorMessage: string = '';

  constructor(private rdfService: RDFService) {}

  ngOnInit(): void {
    this.loadRDFData();
  }

  loadRDFData(): void {
    this.rdfService.getCombinedRDF().subscribe(
      data => this.rdfData = data,
      error => this.errorMessage = 'Error loading RDF data'
    );
  }

  exportRDF(): void {
    const blob = new Blob([this.rdfData], { type: 'application/xml' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'combined.rdf';
    a.click();
    window.URL.revokeObjectURL(url);
  }
}
