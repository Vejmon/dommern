"""
"""
import logging
import typer

app = typer.Typer()

@app.command()
def deploy():
    logging.info("Deploying current build with tag")

if __name__ == "__main__":
    app()