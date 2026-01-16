import typer

from .deploy import gh

app = typer.Typer(help="Dommern CLI")
app.add_typer(gh.app, name="gh")

def main():
    app()

if __name__ == "__main__":
    main()